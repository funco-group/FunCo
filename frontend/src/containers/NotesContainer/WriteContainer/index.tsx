'use client'

/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable @next/next/no-img-element */

import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'
import CoinSelect from '@/components/Note/Write/CoinSelect'
import { ThumbnailImageType } from '@/interfaces/note/ThumbnailImageType'
import { Editor } from '@toast-ui/react-editor'
import dynamic from 'next/dynamic'
import { useRouter } from 'next/navigation'
import { ChangeEvent, useRef, useState } from 'react'
import DelSVG from '@/../public/icon/x-circle-trans.svg'
import palette from '@/lib/palette'

const ToastEditor = dynamic(
  () => import('@/components/Common/ToastUI/ToastEditor'),
  { ssr: false },
)

function NotesWrite() {
  const [coinList, setCoinList] = useState<string[]>([])
  const [titleText, setTitleText] = useState<string>('')
  const [imageList, setImageList] = useState<ThumbnailImageType[]>([])
  const editorRef = useRef<Editor>(null)
  const router = useRouter()

  const handleTitleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setTitleText(e.target.value)
  }

  const handleCancelBtn = () => {
    router.push('/notes')
  }

  const handleSaveBtn = () => {
    const htmlContent = editorRef.current?.getInstance().getHTML()
    console.log(htmlContent)
  }

  const handleOnMouse = (idx: number, bool: boolean) => {
    setImageList((prev) =>
      prev.map((prevImage, prevIdx) =>
        idx === prevIdx ? { ...prevImage, onMouse: bool } : prevImage,
      ),
    )
  }

  const handleDeleteImage = (idx: number) => {
    let newImageList = [...imageList].filter(
      (image, prevIdx) => idx !== prevIdx,
    )
    if (imageList[idx].thumbnail) {
      newImageList = newImageList.map((image, newIdx) =>
        newIdx === 0
          ? { ...image, thumbnail: true }
          : { ...image, thumbnail: false },
      )
    }
    setImageList(newImageList)
  }

  const handleChangeThumbnail = (idx: number) => {
    const newImageList = [...imageList].map((image, newIdx) =>
      newIdx === idx
        ? { ...image, thumbnail: true }
        : { ...image, thumbnail: false },
    )
    setImageList(newImageList)
  }

  const filledTitleClasses = titleText
    ? `border-brandColor border-2`
    : 'border-deactivatedGray border'

  return (
    <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
      <CoinSelect coinList={coinList} setCoinList={setCoinList} />
      <input
        type="text"
        value={titleText}
        onChange={handleTitleChange}
        placeholder="제목을 입력해주세요."
        className={`mt-3 h-9 w-2/4 rounded border-solid outline-none focus:border-2 focus:border-brandColor ${filledTitleClasses}`}
      />
      <ToastEditor editorRef={editorRef} setImageList={setImageList} />
      {imageList.length > 0 ? null : (
        <p className="ml-3 mt-1 text-xs text-brandRed">
          · 최소 1개 이상의 이미지(차트)가 필요합니다.
        </p>
      )}
      <div className="mt-3 flex justify-between">
        <div className="flex gap-1">
          {imageList.map((image, idx) => (
            <div
              className="relative"
              key={idx}
              onMouseEnter={() => handleOnMouse(idx, true)}
              onMouseLeave={() => handleOnMouse(idx, false)}
            >
              <DelSVG
                fill={palette.brandColor}
                className={`absolute right-[-5px] top-[-5px] ${image.onMouse ? null : 'hidden'}`}
                onClick={() => handleDeleteImage(idx)}
              />
              <img
                src={image.src}
                alt="thumbnail-list"
                className={`h-11 rounded border-2 border-solid ${image.thumbnail ? 'border-brandColor' : 'border-transparent'}`}
                onClick={() => handleChangeThumbnail(idx)}
              />
            </div>
          ))}
        </div>
        <div className="flex justify-end gap-2">
          <BrandButtonComponent
            content="취소"
            color={null}
            onClick={handleCancelBtn}
            cancel
            disabled={false}
          />
          <BrandButtonComponent
            content="저장"
            color={null}
            onClick={handleSaveBtn}
            cancel={false}
            disabled={!coinList || !titleText}
          />
        </div>
      </div>
    </div>
  )
}

export default NotesWrite
