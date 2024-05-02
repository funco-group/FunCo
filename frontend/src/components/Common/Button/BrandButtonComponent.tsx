import BrandButton from "./BrandButtonComponent.styled";

interface BrandButtonComponentProps {
  color: string | null;
  content: string;
  onClick: () => void;
  cancel: boolean;
  disabled: boolean;
}

function BrandButtonComponent({
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
      {content}
    </BrandButton>
  );
}

export default BrandButtonComponent;
