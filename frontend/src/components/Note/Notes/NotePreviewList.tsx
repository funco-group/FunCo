import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import { Dispatch, SetStateAction } from 'react'
import NotePreview from './NotePreview'

interface NotePreviewListProps {
  notePreviewList: NotePreviewType[]
  setCoinList: Dispatch<SetStateAction<string[]>>
  // eslint-disable-next-line react/require-default-props
  lastNoteElementRef?: (node: HTMLElement | null) => void
}

function NotePreviewList({
  notePreviewList,
  setCoinList,
  lastNoteElementRef,
}: NotePreviewListProps) {
  return (
    <div className="mt-5 grid grid-cols-3 gap-8">
      {notePreviewList?.map((data, index) => (
        <div
          key={data.noteId}
          ref={index === notePreviewList.length - 1 ? lastNoteElementRef : null}
        >
          <NotePreview
            notePreviewData={data}
            key={data.noteId}
            setCoinList={setCoinList}
          />
        </div>
      ))}
    </div>
  )
}

export default NotePreviewList
