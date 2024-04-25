import {
  MemberInfoContainer,
  MemberInfo,
  TeamInfo,
  FooterContainer,
} from "./styled";
import githubIcon from "@/assets/icon/github-icon.png";

function index() {
  const infos = [
    {
      name: "Woonggss",
      link: "https://github.com/Woonggss",
    },
    {
      name: "jaeyeol",
      link: "https://github.com/devjy39",
    },
    {
      name: "sohy19",
      link: "https://github.com/sohy19",
    },
    {
      name: "sunju5402",
      link: "https://github.com/sunju5402",
    },
    {
      name: "leetaggg",
      link: "https://github.com/leetaggg",
    },
    {
      name: "Hot-ttu",
      link: "https://github.com/Hot-ttu",
    },
  ];

  const clickMember = (link: string) => {
    window.open(link);
  };

  return (
    <FooterContainer>
      <TeamInfo>@SSAFY 404 FOUND</TeamInfo>
      <MemberInfoContainer>
        {infos.map((info) => (
          <MemberInfo onClick={() => clickMember(info.link)}>
            <img src={githubIcon} alt={info.name} width={20} />
            <span>{info.name}</span>
          </MemberInfo>
        ))}
      </MemberInfoContainer>
    </FooterContainer>
  );
}

export default index;
