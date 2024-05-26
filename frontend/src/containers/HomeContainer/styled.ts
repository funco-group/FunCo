import styled from 'styled-components'
import palette from '@/lib/palette'

export const Background = styled.div`
  height: calc(100vh - 10rem);
  display: flex;
  flex-direction: column;
  align-items: center;
  vertical-align: middle;
  position: relative;
`

export const HomePageColumnGridDiv = styled.div`
  display: grid;
  grid-template-columns: 1fr 28rem 34rem 28rem 1fr;
  height: 25rem;
`

export const HomePageRowGridDiv = styled.div`
  display: grid;
  grid-template-rows: 1fr 1fr;
`

export const HomePageFlexDiv = styled.div<{ direction: string }>`
  display: flex;
  align-items: center;
  justify-content: ${({ direction }) => direction};
`

export const PreviewDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`

export const PreviewInnerDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 0.625rem;
`

export const PreviewImage = styled.img`
  width: 30rem;
  opacity: 0;
  transition: opacity 0.5s ease-in-out;
`

export const TextDiv = styled.div`
  text-align: center;
`
export const MainTextDiv = styled.div`
  font-family: 'NanumSquareBold';
  font-size: 2rem;
`
export const SubTextDiv = styled.div`
  font-size: 1.1rem;
  padding: 0.7rem;
  color: ${palette.brandDarkGray};
  span {
    color: ${palette.brandColor};
    font-family: 'NanumSquareBold';
  }
`

export const Content = styled.div`
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
`
