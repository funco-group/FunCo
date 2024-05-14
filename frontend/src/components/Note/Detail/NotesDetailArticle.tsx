'use client'

/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable @next/next/no-img-element */
import DummyNotes from '@/lib/DummyNote'
import noteParseDate from '@/utils/noteParseDate'
import dynamic from 'next/dynamic'
import LikeSVG from '@/../public/icon/like.svg'
import { useEffect, useState } from 'react'

const ToastViewer = dynamic(
  () => import('@/components/Common/ToastUI/ToastViewer'),
  {
    ssr: false,
  },
)

interface NotesDetailArticleProps {
  noteId: number
}

function NotesDetailArticle({ noteId }: NotesDetailArticleProps) {
  const detail = DummyNotes[noteId - 1]
  const [isLike, setIsLike] = useState(detail.liked)
  const [likeCnt, setLikeCnt] = useState(
    detail.liked ? detail.likeCount - 1 : detail.likeCount + 1,
  )

  const handleIsLikeBtn = () => {
    setIsLike((prev) => !prev)
  }

  useEffect(() => {
    setLikeCnt((prev) => (isLike ? prev + 1 : prev - 1))
  }, [isLike])
  return (
    <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
      <div className="w-fit rounded bg-brandColor p-1 text-brandWhite">
        {detail.coinName}
      </div>
      <h2>{detail.title}</h2>
      <div className="flex">
        <img
          src={detail.profileImage}
          className="block h-10 w-10 rounded-full"
          alt="profile"
        />
        <div className="ml-2 flex flex-col justify-center text-xs">
          <div>{detail.nickname}</div>
          <div className=" text-brandDarkGray">
            {noteParseDate(detail.writeDate)}
          </div>
        </div>
      </div>
      <div className="my-3">
        <ToastViewer initialValue={detail.content} />
      </div>
      <div className="mb-5 mt-8 flex justify-center">
        <div
          className="flex cursor-pointer select-none items-center gap-2 rounded-xl border border-solid px-6 py-2 outline-none"
          onClick={handleIsLikeBtn}
        >
          <div className="mt-2">
            {isLike ? (
              <LikeSVG fill="red" />
            ) : (
              <LikeSVG fill="transparent" stroke="black" />
            )}
          </div>
          <div className="font-NSB text-2xl">{likeCnt}</div>
        </div>
      </div>
    </div>
  )
}

export default NotesDetailArticle
