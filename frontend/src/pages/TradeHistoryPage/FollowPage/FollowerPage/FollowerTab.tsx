import TabButton from "@/components/common/Button/TabButton.styled";
import FollowerTabContainer from "./FollowerTab.styled";

interface FollowerTabProps {
  nowTabName: string;
  setNowTabName: React.Dispatch<
    React.SetStateAction<"all" | "following" | "settled">
  >;
}

function FollowerTab({ nowTabName, setNowTabName }: FollowerTabProps) {
  const tabNameList: [
    string,
    "all" | "following" | "settled",
    "right" | "left" | "",
  ][] = [
    ["전체", "all", "left"],
    ["팔로잉", "following", ""],
    ["정산 완료", "settled", "right"],
  ];

  const handleTabClick = (selectedTabName: "all" | "following" | "settled") => {
    setNowTabName(selectedTabName);
  };

  return (
    <FollowerTabContainer>
      {tabNameList.map((tabName) => (
        <TabButton
          key={tabName[1]}
          width="5rem"
          height="2.1875rem"
          $active={tabName[1] === nowTabName}
          onClick={() => handleTabClick(tabName[1])}
          radius={tabName[2]}
        >
          {tabName[0]}
        </TabButton>
      ))}
    </FollowerTabContainer>
  );
}

export default FollowerTab;
