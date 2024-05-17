/* eslint-disable @next/next/no-img-element */
import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import noteParseDate from '@/utils/noteParseDate'
import { useRouter } from 'next/navigation'
import { Dispatch, SetStateAction, useState } from 'react'
import NotesDetailCommentInput from './NotesDetailCommentInput'

interface NotesDetailCommentProps {
  noteId: number
  commentData: NoteCommentType
  setCommentList: Dispatch<SetStateAction<NoteCommentType[]>>
  setCommentCnt: Dispatch<SetStateAction<number>>
  isParent: boolean
}

function NotesDetailComment({
  noteId,
  commentData,
  setCommentList,
  setCommentCnt,
  isParent,
}: NotesDetailCommentProps) {
  const [openReply, setOpenReply] = useState(false)
  const router = useRouter()

  const { user } = useUserState()

  const handleClickProfile = () => {
    router.push(`/member/${commentData.member.id}`)
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
      <div className="mt-3 box-border flex gap-3 ">
        <img
          src={commentData.member.profileUrl}
          className="block h-12 w-12 cursor-pointer rounded-full"
          alt="profile"
          onClick={handleClickProfile}
        />
        <div className="flex w-[calc(100%-3.75rem)] flex-col justify-center  text-xs">
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
            </div>
          </div>
          <div className="w-fit rounded border-none bg-brandColor2 p-4">
            {commentData.content}
          </div>
          <div
            className={`mt-1 flex select-none gap-1 text-brandDarkGray ${openReply && 'mb-1'}`}
          >
            {user?.memberId === commentData.member.id ? (
              <div className="flex gap-1">
                <span className="cursor-pointer" onClick={handleModifyComment}>
                  수정
                </span>
                <span className="cursor-pointer" onClick={handleDeleteComment}>
                  삭제
                </span>
              </div>
            ) : null}
            {isParent && (
              <span className="cursor-pointer" onClick={handleReplyComment}>
                {openReply ? '닫기' : '답글'}
              </span>
            )}
          </div>
          {openReply ? (
            <NotesDetailCommentInput
              noteId={noteId}
              parentCommentId={commentData.commentId}
              setCommentList={setCommentList}
              setCommentCnt={setCommentCnt}
            />
          ) : null}
          {commentData.childComments
            ? commentData.childComments.map((childComment) => (
                <NotesDetailComment
                  key={childComment.commentId}
                  noteId={noteId}
                  commentData={childComment}
                  setCommentList={setCommentList}
                  setCommentCnt={setCommentCnt}
                  isParent={false}
                />
              ))
            : null}
        </div>
      </div>
    </div>
  )
}

export default NotesDetailComment
