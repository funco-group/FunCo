import palette from '@/lib/palette'
import styled from 'styled-components'

export const NoteListFirstRowContainer = styled.div`
  display: flex;
`

export const NoteListFilterBtn = styled.button<{ $active: boolean }>`
  border-radius: 0.3125rem;
  width: 80px;
  height: 35px;
  border: ${({ $active }) =>
    $active
      ? `2px solid ${palette.brandColor}`
      : `1px solid ${palette.deactivatedGray}`};
  background-color: ${palette.brandWhite};
  cursor: pointer;
`
