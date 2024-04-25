import styled from "styled-components";
import { ReactNodeProps } from "@/interfaces/common/ReactNodeProps";

const NarrowLayoutContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 57.5rem 1fr;
  /* background-color: red; */
`;

function NarrowLayout({ children }: ReactNodeProps) {
  return (
    <NarrowLayoutContainer>
      <div />
      <div>{children}</div>
      <div />
    </NarrowLayoutContainer>
  );
}

export default NarrowLayout;
