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

const ToastEditor = dynamic(
  () => import('@/components/Common/ToastUI/ToastEditor'),
  { ssr: false },
)

function NotesWrite() {
  const [coin, setCoin] = useState<string>('')
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
    console.log('title', titleText)
    console.log('content', htmlContent)
    console.log('ticker', coin)
    console.log(
      'thumbnailImage',
      imageList.find((image) => image.thumbnail)?.src,
    )
  }

  const handleOnMouse = (src: string, bool: boolean) => {
    setImageList((prev) =>
      prev.map((prevImage) =>
        src === prevImage.src ? { ...prevImage, onMouse: bool } : prevImage,
      ),
    )
  }

  const handleChangeThumbnail = (src: string) => {
    const newImageList = [...imageList].map((image) => ({
      ...image,
      thumbnail: src === image.src,
    }))
    setImageList(newImageList)
  }

  const filledTitleClasses = titleText
    ? `border-brandColor border-2`
    : 'border-deactivatedGray border'

  return (
    <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
      <CoinSelect coin={coin} setCoin={setCoin} />
      <input
        type="text"
        value={titleText}
        onChange={handleTitleChange}
        placeholder="제목을 입력해주세요."
        className={`mt-3 h-9 w-2/4 rounded border-solid outline-none focus:border-2 focus:border-brandColor ${filledTitleClasses}`}
      />
      <ToastEditor
        editorRef={editorRef}
        imageList={imageList}
        setImageList={setImageList}
      />

      <div className="mt-3 flex h-[52px] justify-between">
        {imageList.length > 0 ? (
          <div className="flex gap-1">
            {imageList.map((image) => (
              <div
                className="relative hover:cursor-pointer"
                key={image.src}
                onMouseEnter={() => handleOnMouse(image.src, true)}
                onMouseLeave={() => handleOnMouse(image.src, false)}
              >
                <img
                  src={image.src}
                  alt="thumbnail-list"
                  className={`h-12 rounded border-4 border-solid ${image.thumbnail ? 'border-brandColor' : 'border-transparent'} ${image.onMouse ? 'scale-110 transform' : null}`}
                  onClick={() => handleChangeThumbnail(image.src)}
                />
              </div>
            ))}
          </div>
        ) : (
          <p className="ml-3 mt-1 text-xs text-brandRed">
            · 최소 1개 이상의 이미지(차트)가 필요합니다.
          </p>
        )}
        <div className="flex items-end justify-end gap-2">
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
            disabled={!coin || !titleText || imageList.length === 0}
          />
        </div>
      </div>
    </div>
  )
}

export default NotesWrite
