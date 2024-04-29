import styled from 'styled-components'
import palette from '@/lib/palette'

export const HomePageButton = styled.button<{
  direction: string
  $number: number
}>`
  display: block;
  width: 28rem;
  img {
    margin: ${({ direction }) =>
      direction === 'right' ? '0 0 50px 50px' : '0 50px 50px 0'};
  }

  margin-right: 0;
  margin-top: ${({ $number }) =>
    $number === 2 || $number === 3 ? '100px' : null};
  text-align: ${({ direction }) => direction};
  border: none;
  background-color: transparent;
  cursor: pointer;
`

export const HomePageButtonFlexDiv = styled.div<{ direction: string }>`
  display: flex;
  justify-content: ${({ direction }) => direction};
`

export const HomePageButtonTitleDiv = styled.div`
  color: ${palette.mainColor2};
  font-size: 1.2rem;
  margin-bottom: 0.625rem;
  font-family: 'NanumSquareBold';
`

export const HomePageButtonContentDiv = styled.div<{ $active: boolean }>`
  display: flex;
  flex-direction: column;
  color: ${({ $active }) =>
    $active ? palette.brandColor : palette.borderGray};
  font-size: ${({ $active }) => ($active ? '1.1rem' : '1rem')};
  font-family: ${({ $active }) =>
    $active ? 'NanumSquareBold' : 'NanumSquare'};
`
