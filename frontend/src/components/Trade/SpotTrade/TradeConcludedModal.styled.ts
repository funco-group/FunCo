import styled from "styled-components";
import palette from "@/lib/palette";

const TypeDiv = styled.div<{ name: string }>`
  color: ${(props) =>
    props.name === "매도" ? palette.brandBlue : palette.brandRed};
`;

export default TypeDiv;
