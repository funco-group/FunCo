import styled from "styled-components";
import palette from "@/lib/palette";

const FollowTabContainer = styled.div`
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 1.25rem 0;
  border-bottom: 1px solid ${palette.deactivatedGray};
`;

export default FollowTabContainer;
