'use client'

import { useEffect, useState } from 'react'
import HomePageButtonComponent from '@/components/Home/HomePageButtonComponent'
import preview0 from '@/../public/image/preview0.png'
import preview1 from '@/../public/image/preview1.png'
import preview2 from '@/../public/image/preview2.png'
import preview3 from '@/../public/image/preview3.png'
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

  const bgList = [preview0, preview1, preview2, preview3]

  return (
    <Background>
      <Content>
        <TextDiv>
          <MainTextDiv>가상 모의투자 플랫폼</MainTextDiv>
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
                src={bgList[nowTabNumber].src}
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
