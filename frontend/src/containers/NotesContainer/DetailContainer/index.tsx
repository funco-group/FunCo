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

// async function getArticleData(noteId: number) {
//   const res = await fetch(
//     `${process.env.NEXT_PUBLIC_BASE_URL}/v1/notes/${noteId}`,
//     {
//       cache: 'no-store',
//     },
//   )
// }

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
