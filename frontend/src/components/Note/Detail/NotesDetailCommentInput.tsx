import useUserState from '@/hooks/recoilHooks/useUserState'
import { ChangeEvent, useState } from 'react'

function NotesDetailCommentInput() {
  const [commentText, setCommentText] = useState('')
  const { user } = useUserState()

  const handleCommentChange = (event: ChangeEvent<HTMLInputElement>) => {
    setCommentText(event.target.value)
  }

  const handleClickSubmitBtn = () => {
    console.log(commentText)
  }

  return (
    <div className="mb-4 border-x-0 border-b border-t-0 border-solid pb-4">
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
    </div>
  )
}

export default NotesDetailCommentInput
