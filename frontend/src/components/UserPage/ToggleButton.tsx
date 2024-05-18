import { useEffect } from 'react'
import {
  DivToggleButtonCircle,
  DivToggleButtonContainer,
} from './ToggleButton.styled'

interface ToggleButtonProps {
  width: string
  height: string
  isToggleOn: boolean
  handleClick: () => void
}

function ToggleButton({
  width,
  height,
  isToggleOn,
  handleClick,
}: ToggleButtonProps) {
  useEffect(() => {
    console.log(isToggleOn)
  }, [isToggleOn])

  return (
    <DivToggleButtonContainer
      className={isToggleOn ? 'toggle-on' : undefined}
      onClick={handleClick}
      width={width}
      height={height}
    >
      <DivToggleButtonCircle className={isToggleOn ? 'toggle-on' : undefined} />
    </DivToggleButtonContainer>
  )
}

export default ToggleButton
