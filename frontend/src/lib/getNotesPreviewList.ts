import DummyNotes from './DummyNote'

const getNotesPreviewList = (link: string) => {
  if (link.includes('MY')) {
    if (link.includes('search')) {
      return DummyNotes
    }
    return DummyNotes.filter((note) => note.noteId === 1)
  }
  if (link.includes('LIKE')) {
    return DummyNotes.filter((note) => note.liked)
  }
  return DummyNotes
}

export default getNotesPreviewList
