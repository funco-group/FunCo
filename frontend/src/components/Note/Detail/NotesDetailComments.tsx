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
        const scrollPoint = document.getElementById(`${scroll}`)?.offsetTop
        if (scrollPoint !== undefined) {
          window.scroll({
            top: scrollPoint - 140,
            behavior: 'smooth',
          })
        }
      }
    }
  }, [])

  return (
    <div id="comments">
      <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
        <div className="mb-4 border-x-0 border-b border-t-0 border-solid pb-4">
          <NotesDetailCommentInput
            parentCommentId={null}
            setCommentList={setCommentList}
          />
        </div>
        {commentList.length > 0 ? (
          commentList.map((comment) => (
            <NotesDetailComment
              commentData={comment}
              key={comment.commentId}
              setCommentList={setCommentList}
              isParent
            />
          ))
        ) : (
          <h1>댓글이 아직 없습니다.</h1>
        )}
      </div>
    </div>
  )
}

export default NotesDetailComments
