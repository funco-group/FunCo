'use client'

import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'
import CoinSelect from '@/components/Note/Write/CoinSelect'
import dynamic from 'next/dynamic'
import { useRouter } from 'next/navigation'
import { ChangeEvent, useState } from 'react'

const ToastEditor = dynamic(
  () => import('@/components/Common/ToastUI/ToastEditor'),
  { ssr: false },
)

function NotesWrite() {
  const [coinList, setCoinList] = useState<string[]>([])
  const [titleText, setTitleText] = useState<string>('')
  const router = useRouter()

  const handleTitleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setTitleText(e.target.value)
  }

  const handleCancelBtn = () => {
    router.push('/notes')
  }

  const handleSaveBtn = () => {}

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
      <ToastEditor />
      <div className="mt-3 flex justify-end gap-2">
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
  )
}

export default NotesWrite
