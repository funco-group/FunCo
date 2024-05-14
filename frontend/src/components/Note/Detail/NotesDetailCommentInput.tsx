import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react'

interface NotesDetailCommentInputProps {
  parentCommentId: number | null
  setCommentList: Dispatch<SetStateAction<NoteCommentType[]>>
}

function NotesDetailCommentInput({
  parentCommentId,
  setCommentList,
}: NotesDetailCommentInputProps) {
  const [commentText, setCommentText] = useState('')
  const { user } = useUserState()

  const handleCommentChange = (event: ChangeEvent<HTMLInputElement>) => {
    setCommentText(event.target.value)
  }

  const handleClickSubmitBtn = () => {
    if (parentCommentId) {
      setCommentList((prev) => {
        const newList = [...prev]
        const idx = newList.findIndex(
          (cmt) => cmt.commentId === parentCommentId,
        )
        if (newList[idx].childComments) {
          newList[idx].childComments?.push({
            commentId: 7,
            member: {
              memberId: 6,
              nickname: '슈퍼 민희언',
              profileUrl: '/image/chuu.gif',
            },
            content: commentText,
            date: '2024-01-20T15:00:00',
          })
        } else {
          newList[idx].childComments = [
            {
              commentId: 7,
              member: {
                memberId: 6,
                nickname: '슈퍼 민희언',
                profileUrl: '/image/chuu.gif',
              },
              content: commentText,
              date: '2024-01-20T15:00:00',
            },
          ]
        }
        return newList
      })
      console.log(commentText, parentCommentId)
    } else {
      setCommentList((prev) => [
        ...prev,
        {
          commentId: 7,
          member: {
            memberId: 6,
            nickname: '슈퍼 민희언',
            profileUrl: '/image/chuu.gif',
          },
          content: commentText,
          date: '2024-01-20T15:00:00',
        },
      ])
      console.log(commentText)
    }
  }

  return (
    <div className="rounded-lg border border-solid p-2">
      {user ? (
        <div>
          <div className="text-sm">{user.nickname}</div>
          <input
            type="text"
            value={commentText}
            onChange={handleCommentChange}
            placeholder="댓글을 입력해주세요"
            className="w-full border-none outline-none"
          />
          <button
            type="button"
            className="ml-auto mr-0 block h-7 rounded border-none bg-brandColor px-2 font-NSB text-xs text-brandWhite"
            onClick={handleClickSubmitBtn}
          >
            등록
          </button>
        </div>
      ) : (
        <div>로그인 후 댓글을 작성할 수 있습니다.</div>
      )}
    </div>
  )
}

export default NotesDetailCommentInput
