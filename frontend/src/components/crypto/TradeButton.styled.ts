import styled from "styled-components";
import palette from "@/lib/palette";

const TradeButtonContainer = styled.div<{ name: string }>`
  background-color: ${(props) =>
    props.name === "매수" ? palette.brandRed : palette.brandBlue};
  color: ${palette.brandWhite};
  text-align: center;
  padding: 0.6rem;
  margin: 1rem 0.7rem;
  cursor: pointer;

  &:hover {
    opacity: 95%;
  }
`;

export default TradeButtonContainer;
