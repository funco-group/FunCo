import { getCommentsData } from '@/apis/note'
import NotesDetailComments from '@/components/Note/Detail/NotesDetailComments'
import dynamic from 'next/dynamic'

const NotesDetailArticle = dynamic(
  () => import('@/components/Note/Detail/NotesDetailArticle'),
  { ssr: false },
)

interface NotesDetailProps {
  noteId: number
}

async function NotesDetail({ noteId }: NotesDetailProps) {
  const initialCommentList = await getCommentsData(noteId)
  return (
    <div>
      <NotesDetailArticle noteId={noteId} />
      <NotesDetailComments
        noteId={noteId}
        initialCommentList={initialCommentList}
      />
    </div>
  )
}

export default NotesDetail
