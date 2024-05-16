import TradeButtonContainer from "./TradeButton.styled";

interface Props {
  name: string;
  onclick: () => void;
}

function TradeButton({ name, onclick }: Props) {
  return (
    <TradeButtonContainer name={name} onClick={onclick}>
      {name} 주문
    </TradeButtonContainer>
  );
}

export default TradeButton;
