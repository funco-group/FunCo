import BrandButton from './BrandButtonComponent.styled'

interface BrandButtonComponentProps {
  imgSrc: string
  color: string | null
  content: string
  onClick: () => void
  cancel: boolean
  disabled: boolean
}

function BrandButtonComponent({
  imgSrc,
  content,
  color,
  cancel,
  onClick,
  disabled,
}: BrandButtonComponentProps) {
  return (
    <BrandButton
      type="button"
      color={color}
      onClick={onClick}
      $cancel={cancel}
      disabled={disabled}
    >
      {imgSrc && <img src={imgSrc} alt={imgSrc} width={14} />}
      {content}
    </BrandButton>
  )
}

export default BrandButtonComponent
