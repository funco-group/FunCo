import { NotePreviewType } from '@/interfaces/note/notePreviewType'
import DummyNotes from '@/lib/DummyNote'
import { useEffect, useState } from 'react'
import NotePreview from './NotePreview'

function NotePreviewList() {
  const [NotePreviewDataList, setNotePreivewDataList] =
    useState<NotePreviewType[]>()

  useEffect(() => {
    setNotePreivewDataList(DummyNotes)
  }, [])
  return (
    <div className="mt-5 grid grid-cols-3 gap-8">
      {NotePreviewDataList?.map((data) => (
        <NotePreview notePreviewData={data} />
      ))}
    </div>
  )
}

export default NotePreviewList
