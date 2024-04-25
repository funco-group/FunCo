import styled from "styled-components";
import palette from "@/lib/palette";

const BodyContainer = styled.div<{ $main: boolean }>`
  padding: 6.5rem 0;
  background-color: ${({ $main }) => ($main ? palette.brandWhite : null)};
`;

export default BodyContainer;
