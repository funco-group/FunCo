import NotesDetailComments from '@/components/Note/Detail/NotesDetailComments'
import DummyComments from '@/lib/DummyComments'
import dynamic from 'next/dynamic'

const NotesDetailArticle = dynamic(
  () => import('@/components/Note/Detail/NotesDetailArticle'),
  { ssr: false },
)

interface NotesDetailProps {
  noteId: number
}

function NotesDetail({ noteId }: NotesDetailProps) {
  const initialCommentList = DummyComments
  return (
    <div>
      <NotesDetailArticle noteId={noteId} />
      <NotesDetailComments initialCommentList={initialCommentList} />
    </div>
  )
}

export default NotesDetail
