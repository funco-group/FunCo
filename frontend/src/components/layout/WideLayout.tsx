import styled from "styled-components";
import { ReactNodeProps } from "@/interfaces/common/ReactNodeProps";

const WideLayoutContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 85rem 1fr;
  /* background-color: red; */
`;

function WideLayout({ children }: ReactNodeProps) {
  return (
    <WideLayoutContainer>
      <div />
      <div>{children}</div>
      <div />
    </WideLayoutContainer>
  );
}

export default WideLayout;
