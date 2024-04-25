import { ListItemDiv, ColumnGrid } from "@/styles/CommonStyled";
import { ListItemContainer } from "@/styles/ListItemContainer";
import { ReturnResultListItemContainer } from "./ReturnResultListItem.styled";
import { StatisticsType } from "@/interfaces/StatisticsType";

interface ReturnResultListItemProps {
  result: StatisticsType;
}

function ReturnResultListItem({ result }: ReturnResultListItemProps) {
  return (
    <ListItemContainer>
      <ReturnResultListItemContainer>
        <ColumnGrid column="1fr 0.8fr 0.6fr 1fr 0.6fr 1fr 1fr">
          <ListItemDiv color="black" align="left">
            {result.date}
          </ListItemDiv>
          <ListItemDiv
            color={
              result.returnResult.toString().startsWith("-") ? "blue" : "red"
            }
            align="right"
          >
            {result.returnResult.toLocaleString("ko-KR")}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv
            color={
              result.returnRate.toString().startsWith("-") ? "blue" : "red"
            }
            align="right"
          >
            {result.returnRate}
            <span>%</span>
          </ListItemDiv>
          <ListItemDiv
            color={
              result.accReturnResult.toString().startsWith("-") ? "blue" : "red"
            }
            align="right"
          >
            {result.accReturnResult.toLocaleString("ko-KR")}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv
            color={
              result.accReturnRate.toString().startsWith("-") ? "blue" : "red"
            }
            align="right"
          >
            {result.accReturnRate}
            <span>%</span>
          </ListItemDiv>
          <ListItemDiv color="black" align="right">
            {result.beginningAsset.toLocaleString("ko-KR")}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv color="black" align="right">
            {result.endingAsset.toLocaleString("ko-KR")}
            <span>WON</span>
          </ListItemDiv>
        </ColumnGrid>
      </ReturnResultListItemContainer>
    </ListItemContainer>
  );
}

export default ReturnResultListItem;
