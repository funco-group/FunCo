import palette from '@/lib/palette'
import styled from 'styled-components'

export const DivToggleButtonContainer = styled.div<{
  width: string
  height: string
}>`
  position: relative;
  border-radius: 20px;
  cursor: pointer;
  margin-right: 0.2rem;

  width: ${(props) => props.width};
  height: ${(props) => props.height};

  background-color: rgb(233, 233, 234);
  transition: 0.5s;

  &.toggle-on {
    background-color: ${palette.brandColor};
  }
`

export const DivToggleButtonCircle = styled.div`
  position: absolute;
  top: 10%;
  left: 5%;

  border: none;
  border-radius: 100%;

  height: 80%;
  aspect-ratio: 1/1;

  background-color: rgb(255, 254, 255);

  &.toggle-on {
    left: 49%;
  }
  transition: 0.3s;
`
