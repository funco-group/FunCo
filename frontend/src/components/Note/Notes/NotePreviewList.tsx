import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import { Dispatch, SetStateAction } from 'react'
import NotePreview from './NotePreview'

interface NotePreviewListProps {
  notePreviewList: NotePreviewType[]
  setCoinList: Dispatch<SetStateAction<string[]>>
}

function NotePreviewList({
  notePreviewList,
  setCoinList,
}: NotePreviewListProps) {
  return (
    <div className="mt-5 grid grid-cols-3 gap-8">
      {notePreviewList?.map((data) => (
        <NotePreview
          notePreviewData={data}
          key={data.noteId}
          setCoinList={setCoinList}
        />
      ))}
    </div>
  )
}

export default NotePreviewList
