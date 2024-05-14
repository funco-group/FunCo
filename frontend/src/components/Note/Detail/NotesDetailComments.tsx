'use client'

import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import { useSearchParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import dynamic from 'next/dynamic'
import NotesDetailComment from './NotesDetailComment'

const NotesDetailCommentInput = dynamic(
  () => import('@/components/Note/Detail/NotesDetailCommentInput'),
  { ssr: false },
)

interface NotesDetailCommentsProps {
  initialCommentList: NoteCommentType[]
}

function NotesDetailComments({ initialCommentList }: NotesDetailCommentsProps) {
  const searchParams = useSearchParams()
  const [commentList, setCommentList] = useState(initialCommentList)

  useEffect(() => {
    const scroll = searchParams.get('scroll')

    if (scroll) {
      if (typeof window !== 'undefined') {
        window.scroll({
          top: document.getElementById(`${scroll}`)?.offsetTop,
          behavior: 'smooth',
        })
      }
    }
  }, [])

  return (
    <div id="comments">
      <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
        <NotesDetailCommentInput />
        {commentList.length > 0 ? (
          commentList.map((comment) => (
            <NotesDetailComment commentData={comment} key={comment.commentId} />
          ))
        ) : (
          <h1>댓글이 아직 없습니다.</h1>
        )}
      </div>
    </div>
  )
}

export default NotesDetailComments
