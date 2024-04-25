import { Dispatch, SetStateAction } from "react";
import {
  HomePageButton,
  HomePageButtonContentDiv,
  HomePageButtonFlexDiv,
  HomePageButtonTitleDiv,
} from "./HomePageButtonComponent.styled";
import buttonImage from "@/assets/icon/eclipse-button.svg";

interface HomePageButtonComponentProps {
  number: number;
  nowTabNumber: number;
  setNowTabNumber: Dispatch<SetStateAction<number>>;
}

const buttonList = [
  {
    direction: "right",
    title: "실시간 시세에 기반한 모의 투자 플랫폼",
    content: ["실시간 시세로 모의 거래", "실제 투자와 같은 환경에서 연습"],
  },
  {
    direction: "left",
    title: "투자 전략이 궁금한 유저를 팔로우",
    content: ["일정 금액만큼 유저를 팔로우", "자동 투자 및 거래 내역 확인"],
  },
  {
    direction: "left",
    title: "거래 체결 시 알림",
    content: ["팔로우 및 직접 투자 시 알림", "간편하게 현황 확인 가능"],
  },
  {
    direction: "right",
    title: "나는 몇 등 투자자일까?",
    content: ["랭킹 순위를 확인", "플랫폼 내에서의 객관적인 실력을 확인"],
  },
];

function HomePageButtonComponent({
  number,
  nowTabNumber,
  setNowTabNumber,
}: HomePageButtonComponentProps) {
  const handleSetTabNumber = (num: number) => {
    setNowTabNumber(num);
  };

  return (
    <HomePageButton
      $active={nowTabNumber === number}
      direction={buttonList[number].direction}
      number={number}
      onMouseEnter={() => handleSetTabNumber(number)}
    >
      <HomePageButtonFlexDiv direction={buttonList[number].direction}>
        {buttonList[number].direction === "left" && (
          <img src={buttonImage} alt="home-button" />
        )}
        <div>
          <HomePageButtonTitleDiv>
            {buttonList[number].title}
          </HomePageButtonTitleDiv>
          <HomePageButtonContentDiv $active={nowTabNumber === number}>
            <div>{buttonList[number].content[0]}</div>
            <div>{buttonList[number].content[1]}</div>
          </HomePageButtonContentDiv>
        </div>
        {buttonList[number].direction === "right" && (
          <img src={buttonImage} alt="home-button" />
        )}
      </HomePageButtonFlexDiv>
    </HomePageButton>
  );
}

export default HomePageButtonComponent;
