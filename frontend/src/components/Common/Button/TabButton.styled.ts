import styled from 'styled-components'
import palette from '@/lib/palette'

interface TabButtonProps {
  width: string | null
  height: string
  $active: boolean
  radius: 'left' | 'right' | ''
}

const TabButton = styled.button<TabButtonProps>`
  box-sizing: border-box;
  background-color: ${palette.brandWhite};
  width: ${({ width }) => width || 'auto'};
  height: ${({ height }) => height};
  color: ${({ $active }) => ($active ? palette.brandColor : '#999999')};
  padding: 0.4rem;
  border-top: ${({ $active }) =>
    $active
      ? `2px solid ${palette.brandColor}`
      : `2px solid ${palette.deactivatedGray}`};
  border-bottom: ${({ $active }) =>
    $active
      ? `2px solid ${palette.brandColor}`
      : `2px solid ${palette.deactivatedGray}`};
  border-left: ${({ $active, radius }) => {
    if ($active) {
      return `2px  solid ${palette.brandColor}`
    }
    if (radius === 'right') {
      return 'none'
    }
    return `2px  solid ${palette.deactivatedGray}`
  }};
  border-right: ${({ $active, radius }) => {
    if ($active) {
      return `2px  solid ${palette.brandColor}`
    }
    if (radius === 'left') {
      return 'none'
    }
    return `2px  solid ${palette.deactivatedGray}`
  }};
  border-radius: ${({ radius }) => {
    if (radius === 'left') {
      return '5px 0 0 5px'
    }
    if (radius === 'right') {
      return '0 5px 5px 0'
    }
    return '0'
  }};
  font-family: ${({ $active }) =>
    $active ? 'NanumSquareBold' : 'NanumSquare'};
`

export default TabButton
