'use client'

/* eslint-disable @next/next/no-img-element */
import DummyNotes from '@/lib/DummyNote'
import noteParseDate from '@/utils/noteParseDate'
import dynamic from 'next/dynamic'
import LikeSVG from '@/../public/icon/like.svg'

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
      <div className="flex justify-center">
        <div className="rounded border border-solid border-borderGray p-4">
          {detail.liked ? (
            <LikeSVG fill="red" />
          ) : (
            <LikeSVG fill="transparent" stroke="black" />
          )}
        </div>
      </div>
    </div>
  )
}

export default NotesDetailArticle
