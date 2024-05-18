import { getCommentsData, postNotesComment } from '@/apis/note'
import NoData from '@/components/Common/NoData'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import { NoteCommentsType } from '@/interfaces/note/NoteCommentsType'
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react'

interface NotesDetailCommentInputProps {
  noteId: number
  parentCommentId: number | null
  setCommentList: Dispatch<SetStateAction<NoteCommentType[]>>
  setCommentCnt: Dispatch<SetStateAction<number>>
}

function NotesDetailCommentInput({
  noteId,
  parentCommentId,
  setCommentList,
  setCommentCnt,
}: NotesDetailCommentInputProps) {
  const [commentText, setCommentText] = useState('')
  const { user } = useUserState()

  const handleCommentChange = (event: ChangeEvent<HTMLInputElement>) => {
    setCommentText(event.target.value)
  }

  const handleClickSubmitBtn = () => {
    if (parentCommentId) {
      postNotesComment(
        noteId,
        { parentCommentId, content: commentText },
        async () => {
          const commentList: NoteCommentsType = await getCommentsData(noteId)
          console.log(commentList)
          setCommentList(commentList.comments)
          setCommentCnt(commentList.commentCount)
        },
      )
    } else {
      postNotesComment(noteId, { content: commentText }, async () => {
        const commentList: NoteCommentsType = await getCommentsData(noteId)
        setCommentList(commentList.comments)
        setCommentCnt(commentList.commentCount)
      })
      console.log(commentText)
    }
    setCommentText('')
  }

  return (
    <div className="rounded-lg border border-solid p-2">
      {user ? (
        <div>
          <div className="mb-1 text-sm">{user.nickname}</div>
          <input
            type="text"
            value={commentText}
            onChange={handleCommentChange}
            placeholder="댓글을 입력해주세요"
            className="w-full border-none outline-none"
          />
          <button
            type="button"
            className={`ml-auto mr-0 block h-7 rounded border-none bg-brandColor px-2 font-NSB text-xs text-brandWhite ${!commentText.trim() && 'bg-deactivatedGray'}`}
            disabled={!commentText.trim()}
            onClick={handleClickSubmitBtn}
          >
            등록
          </button>
        </div>
      ) : (
        <NoData content="로그인 후 댓글을 작성할 수 있습니다." />
      )}
    </div>
  )
}

export default NotesDetailCommentInput
