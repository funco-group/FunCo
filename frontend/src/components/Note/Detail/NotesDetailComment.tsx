/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable @next/next/no-img-element */
import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import noteParseDate from '@/utils/noteParseDate'
import { useRouter } from 'next/navigation'

interface NotesDetailCommentProps {
  commentData: NoteCommentType
}

function NotesDetailComment({ commentData }: NotesDetailCommentProps) {
  const router = useRouter()

  const { user } = useUserState()

  const handleClickProfile = () => {
    router.push(`/member/${commentData.member.memberId}`)
  }

  const handleModifyComment = () => {
    console.log('mod comment')
  }

  const handleDeleteComment = () => {
    console.log('del comment')
  }

  const handleReplyComment = () => {
    console.log('reply comment')
  }
  return (
    <div>
      <div className="mt-3 box-border flex gap-3">
        <img
          src={commentData.member.profileUrl}
          className="block h-12 w-12 cursor-pointer rounded-full"
          alt="profile"
          onClick={handleClickProfile}
        />
        <div className="flex flex-grow flex-col justify-center text-xs">
          <div className="mb-3">
            <div
              className="w-fit cursor-pointer font-NSB text-base"
              onClick={handleClickProfile}
            >
              {commentData.member.nickname}
            </div>
            <div className="flex justify-between">
              <div className="text-brandDarkGray">
                {noteParseDate(commentData.date)}
              </div>
              {user?.memberId === commentData.member.memberId ? (
                <div className="flex gap-1 text-brandDarkGray">
                  <span
                    className="cursor-pointer"
                    onClick={handleModifyComment}
                  >
                    수정
                  </span>
                  <span
                    className="cursor-pointer"
                    onClick={handleDeleteComment}
                  >
                    삭제
                  </span>
                  <span className="cursor-pointer" onClick={handleReplyComment}>
                    답글
                  </span>
                </div>
              ) : null}
            </div>
          </div>
          <div className="rounded border-none bg-brandColor2 p-4">
            {commentData.content}
          </div>
          {commentData.childComments
            ? commentData.childComments.map((childComment) => (
                <NotesDetailComment commentData={childComment} />
              ))
            : null}
        </div>
      </div>
    </div>
  )
}

export default NotesDetailComment
