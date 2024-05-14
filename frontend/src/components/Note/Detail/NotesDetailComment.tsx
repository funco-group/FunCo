/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable @next/next/no-img-element */
import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import noteParseDate from '@/utils/noteParseDate'
import { useRouter } from 'next/navigation'
import { Dispatch, SetStateAction, useState } from 'react'
import NotesDetailCommentInput from './NotesDetailCommentInput'

interface NotesDetailCommentProps {
  commentData: NoteCommentType
  setCommentList: Dispatch<SetStateAction<NoteCommentType[]>>
}

function NotesDetailComment({
  commentData,
  setCommentList,
}: NotesDetailCommentProps) {
  const [openReply, setOpenReply] = useState(false)
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
    setOpenReply((prev) => !prev)
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
              className="w-fit cursor-pointer font-NSB text-sm"
              onClick={handleClickProfile}
            >
              {commentData.member.nickname}
            </div>
            <div className="flex justify-between">
              <div className="text-brandDarkGray">
                {noteParseDate(commentData.date)}
              </div>
              <div className="flex gap-1 text-brandDarkGray">
                {user?.memberId === commentData.member.memberId ? (
                  <div className="flex gap-1">
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
                  </div>
                ) : null}
                <span className="cursor-pointer" onClick={handleReplyComment}>
                  답글
                </span>
              </div>
            </div>
          </div>
          <div
            className={`rounded border-none bg-brandColor2 p-4 ${openReply ? 'mb-3' : null}`}
          >
            {commentData.content}
          </div>
          {openReply ? (
            <NotesDetailCommentInput
              parentCommentId={commentData.commentId}
              setCommentList={setCommentList}
            />
          ) : null}
          {commentData.childComments
            ? commentData.childComments.map((childComment) => (
                <NotesDetailComment
                  commentData={childComment}
                  setCommentList={setCommentList}
                />
              ))
            : null}
        </div>
      </div>
    </div>
  )
}

export default NotesDetailComment
