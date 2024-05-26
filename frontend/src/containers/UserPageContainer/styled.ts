import styled from 'styled-components'
import palette from '@/lib/palette'

export const ComponentTitleH3 = styled.div`
  padding-bottom: 1rem;
  font-size: 1.2rem;
  font-family: 'NanumSquareBold';
  border-bottom: 1px solid ${palette.deactivatedGray};
`

export const UserLayoutRowDiv = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 1rem;
`
