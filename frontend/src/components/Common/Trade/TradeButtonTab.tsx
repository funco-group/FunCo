import TradeButtonTabContainer from "./TradeButtonTab.styled";

interface Props {
  name: string;
  activeButton: string;
  changeButton: (button: string) => void;
}

function TradeButtonTab({ name, activeButton, changeButton }: Props) {
  return (
    <TradeButtonTabContainer
      name={name}
      active={activeButton}
      onClick={() => changeButton(name)}
    >
      {name}
    </TradeButtonTabContainer>
  );
}

export default TradeButtonTab;
