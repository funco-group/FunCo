import styled from "styled-components";
import palette from "@/lib/palette";

const TradeButtonTabContainer = styled.div<{
  name: string;
  active: string;
}>`
  background-color: ${(props) => {
    if (props.active === props.name) {
      if (props.name === "매수") {
        return palette.brandRed;
      }
      return palette.brandBlue;
    }
    return palette.deactivatedGray;
  }};

  color: ${(props) =>
    props.active === props.name ? palette.brandWhite : palette.brandBlack};
  text-align: center;
  padding: 0.6rem;
  cursor: pointer;
  width: 100%;
`;

export default TradeButtonTabContainer;
