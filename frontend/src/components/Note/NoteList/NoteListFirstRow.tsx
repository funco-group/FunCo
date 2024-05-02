'use client'

import {
  NoteListFilterBtn,
  NoteListFirstRowContainer,
} from './NoteListFirstRow.styled'

function NoteListFirstRow() {
  return (
    <NoteListFirstRowContainer>
      <NoteListFilterBtn $active />
      <NoteListFilterBtn $active={false} />
    </NoteListFirstRowContainer>
  )
}

export default NoteListFirstRow
