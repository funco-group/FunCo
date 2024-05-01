import styled from 'styled-components'
import palette from '@/lib/palette'
import { Overflow } from '@/styles/CommonStyled'

export const FollowerContentTableContainer = styled.div`
  border-top: 1px solid ${palette.deactivatedGray};
`

export const FollowerContentListContainer = styled.div`
  max-height: 40rem;
  ${Overflow}
`
