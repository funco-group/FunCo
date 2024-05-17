'use client'

import { useState } from 'react'
import dynamic from 'next/dynamic'
import { NoteCommentsType } from '@/interfaces/note/NoteCommentsType'
import NoData from '@/components/Common/NoData'
import NotesDetailComment from './NotesDetailComment'

const NotesDetailCommentInput = dynamic(
  () => import('@/components/Note/Detail/NotesDetailCommentInput'),
  { ssr: false },
)

interface NotesDetailCommentsProps {
  noteId: number
  initialCommentList: NoteCommentsType
}

function NotesDetailComments({
  noteId,
  initialCommentList,
}: NotesDetailCommentsProps) {
  const [commentList, setCommentList] = useState(initialCommentList.comments)
  const [commentCnt, setCommentCnt] = useState(initialCommentList.commentCount)

  return (
    <div id="comments">
      <h2>댓글 {commentCnt}개</h2>
      <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
        <div className="mb-4 border-x-0 border-b border-t-0 border-solid pb-4">
          <NotesDetailCommentInput
            noteId={noteId}
            parentCommentId={null}
            setCommentList={setCommentList}
            setCommentCnt={setCommentCnt}
          />
        </div>
        {commentList.length > 0 ? (
          commentList.map((comment) => (
            <NotesDetailComment
              key={comment.commentId}
              noteId={noteId}
              commentData={comment}
              commentList={commentList}
              setCommentList={setCommentList}
              setCommentCnt={setCommentCnt}
              isParent
            />
          ))
        ) : (
          <NoData content="댓글이 아직 없습니다." />
        )}
      </div>
    </div>
  )
}

export default NotesDetailComments
