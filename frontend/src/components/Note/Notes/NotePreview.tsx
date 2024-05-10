/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable @next/next/no-img-element */

import dynamic from 'next/dynamic'
import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import noteParseDate from '@/utils/noteParseDate'
import LikeSVG from '@/../public/icon/like.svg'
import MsgSVG from '@/../public/icon/message-text-alt.svg'
import { useRouter } from 'next/navigation'

const ToastViewer = dynamic(
  () => import('@/components/Common/ToastUI/ToastViewer'),
  {
    ssr: false,
  },
)

interface NotePreviewProps {
  notePreviewData: NotePreviewType
}

function NotePreview({ notePreviewData }: NotePreviewProps) {
  const router = useRouter()

  const buttonDivClasses =
    'flex items-center gap-2 border-solid border-transparent px-2 hover:rounded hover:border-brandColor'

  const handleClickNotesPreview = () => {
    router.push(`/notes/${notePreviewData.noteId}`)
  }

  return (
    <div className="space-y-2 rounded border-2 border-solid border-deactivatedGray bg-brandWhite p-1 hover:border-brandColor">
      <div className="hover:cursor-pointer" onClick={handleClickNotesPreview}>
        <img
          src={notePreviewData.thumbnail}
          alt="preview"
          className="h-44 w-full rounded"
        />
        <div className="w-fit rounded bg-brandColor p-1 text-xs text-brandWhite">
          {notePreviewData.coinName}
        </div>
        <h2 className="font-NSB text-2xl">{notePreviewData.title}</h2>
        <div className="flex">
          <img
            src={notePreviewData.profileImage}
            className="block h-10 w-10 rounded-full"
            alt="profile"
          />
          <div className="ml-2 flex flex-col justify-center text-xs">
            <div>{notePreviewData.nickname}</div>
            <div className=" text-brandDarkGray">
              {noteParseDate(notePreviewData.writeDate)}
            </div>
          </div>
        </div>
        <div className="relative h-40 w-full overflow-hidden">
          <ToastViewer initialValue={notePreviewData.content} />
          <div className="absolute bottom-0 left-0 right-0 h-16 bg-gradient-to-t from-white to-transparent" />
        </div>
      </div>
      <div className="flex justify-end">
        <div className={`${buttonDivClasses}`}>
          <div className="mt-1.5">
            {notePreviewData.liked ? (
              <LikeSVG fill="red" />
            ) : (
              <LikeSVG fill="transparent" stroke="black" />
            )}
          </div>
          <div>{notePreviewData.likeCount}</div>
        </div>
        <div className={`${buttonDivClasses}`}>
          <div className="mt-1.5">
            <MsgSVG />
          </div>
          <div>{notePreviewData.commentCount}</div>
        </div>
      </div>
    </div>
  )
}

export default NotePreview
