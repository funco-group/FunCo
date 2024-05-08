import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import NotePreview from './NotePreview'

interface NotePreviewListProps {
  notePreviewList: NotePreviewType[]
}

function NotePreviewList({ notePreviewList }: NotePreviewListProps) {
  return (
    <div className="mt-5 grid grid-cols-3 gap-8">
      {notePreviewList?.map((data) => (
        <NotePreview notePreviewData={data} key={data.noteId} />
      ))}
    </div>
  )
}

export default NotePreviewList
