import palette from '@/lib/palette'
import styled from 'styled-components'

export const ModalItemDiv = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 1.5rem 0;
`

export const TitleDiv = styled.div`
  font-size: 0.95rem;
`

export const ContentDiv = styled.div`
  font-size: 0.9rem;
  font-family: 'NanumSquareBold';

  span {
    margin-left: 0.2rem;
    font-size: 0.7rem;
    color: ${palette.brandDarkGray};
  }
`
