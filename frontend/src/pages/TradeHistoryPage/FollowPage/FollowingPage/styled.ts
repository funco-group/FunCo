import styled from "styled-components";
import palette from "@/lib/palette";
import { Overflow } from "@/styles/CommonStyled";

const FollowingUserListContainer = styled.div`
  padding: 1rem;
  border-bottom: 1px solid ${palette.deactivatedGray};
  max-height: 30rem;
  ${Overflow}
`;

export default FollowingUserListContainer;
