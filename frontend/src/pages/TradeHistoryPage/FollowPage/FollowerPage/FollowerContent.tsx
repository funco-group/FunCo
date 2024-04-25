import { FollowerContentType } from "@/interfaces/tradeHistory/follow/FollowerContentType";
import useParseDate from "@/hooks/useParseDate";
import { FollowerContentContainer } from "./FollowerContent.styled";
import { ListItemDiv, ColumnGrid } from "@/styles/CommonStyled";
import { ListItemContainer } from "@/styles/ListItemContainer";

function FollowerContent({ content }: { content: FollowerContentType }) {
  const parseDate = useParseDate;
  const followedAt = parseDate(content.followedAt).split(" ").join("\n");
  let settleDate = "-";
  if (content.settleDate) {
    settleDate = parseDate(content.settleDate).split(" ").join("\n");
  }
  return (
    <ListItemContainer>
      <FollowerContentContainer>
        <ColumnGrid column="7.5rem 1fr 1fr 1fr 1fr 1fr 7.5rem">
          <ListItemDiv align="left" color="black">
            {followedAt}
          </ListItemDiv>
          <ListItemDiv align="" color="black">
            {content.nickname}
          </ListItemDiv>
          <ListItemDiv align="right" color="black">
            {content.investment.toLocaleString("ko-KR")} <span>WON</span>
          </ListItemDiv>
          <ListItemDiv align={content.settlement ? "right" : ""} color="black">
            {content.settlement
              ? content.settlement.toLocaleString("ko-KR")
              : "-"}
            {content.settlement && <span>WON</span>}
          </ListItemDiv>
          <ListItemDiv
            align={content.returnRate !== null ? "right" : ""}
            color={
              content.returnRate && content.returnRate > 0 ? "red" : "blue"
            }
          >
            {content.returnRate !== null
              ? content.returnRate.toLocaleString("ko-KR")
              : "-"}
            {content.returnRate && <span>%</span>}
          </ListItemDiv>
          <ListItemDiv
            align={content.commission !== null ? "right" : ""}
            color="black"
          >
            {content.commission !== null
              ? content.commission.toLocaleString("ko-KR")
              : "-"}
            {content.commission !== null && <span>WON</span>}
          </ListItemDiv>
          <ListItemDiv align="right" color="black">
            {settleDate}
          </ListItemDiv>
        </ColumnGrid>
      </FollowerContentContainer>
    </ListItemContainer>
  );
}

export default FollowerContent;
