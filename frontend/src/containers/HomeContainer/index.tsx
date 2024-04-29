'use client'

import { useEffect, useState } from 'react'
import HomePageButtonComponent from '@/components/Home/HomePageButtonComponent'
import {
  HomePageColumnGridDiv,
  HomePageFlexDiv,
  HomePageRowGridDiv,
  PreviewDiv,
  PreviewImage,
  PreviewInnerDiv,
  TextDiv,
  MainTextDiv,
  SubTextDiv,
  Background,
  Content,
} from './styled'

function Home() {
  const [nowTabNumber, setNowTabNumber] = useState(0)

  useEffect(() => {
    const timer = setTimeout(() => {
      setNowTabNumber((nowTabNumber + 1) % 4)
    }, 4000)

    return () => {
      clearTimeout(timer)
    }
  }, [nowTabNumber])

  return (
    <Background>
      <Content>
        <TextDiv>
          <MainTextDiv>가상화폐 모의투자 플랫폼</MainTextDiv>
          <SubTextDiv>
            뻔하게 투자하지 않고 <span>FUN</span>하게 투자
          </SubTextDiv>
        </TextDiv>
        <HomePageColumnGridDiv>
          <div />
          <HomePageRowGridDiv>
            <HomePageFlexDiv direction="right">
              <HomePageButtonComponent
                nowTabNumber={nowTabNumber}
                setNowTabNumber={setNowTabNumber}
                number={0}
              />
            </HomePageFlexDiv>
            <HomePageFlexDiv direction="right">
              <HomePageButtonComponent
                nowTabNumber={nowTabNumber}
                setNowTabNumber={setNowTabNumber}
                number={3}
              />
            </HomePageFlexDiv>
          </HomePageRowGridDiv>
          <PreviewDiv>
            <PreviewInnerDiv>
              <PreviewImage
                src={`/image/preview${nowTabNumber}.png`}
                alt="home-bg"
                style={{ opacity: 1 }}
              />
            </PreviewInnerDiv>
          </PreviewDiv>
          <HomePageRowGridDiv>
            <HomePageFlexDiv direction="left">
              <HomePageButtonComponent
                nowTabNumber={nowTabNumber}
                setNowTabNumber={setNowTabNumber}
                number={1}
              />
            </HomePageFlexDiv>
            <HomePageFlexDiv direction="left">
              <HomePageButtonComponent
                nowTabNumber={nowTabNumber}
                setNowTabNumber={setNowTabNumber}
                number={2}
              />
            </HomePageFlexDiv>
          </HomePageRowGridDiv>
          <div />
        </HomePageColumnGridDiv>
      </Content>
    </Background>
  )
}

export default Home
