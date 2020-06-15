package org.techtown.capstoneprojectcocktail;

public class MJH_Object_ingredient { // 겟셋, 추상화 시간관계상 구현 안할 예정

    String name;
    int type = 0; // 1: 기주, 2: 리큐르, 3: 주스, 4: 시럽, =>DB에서는 별도 테이블?
    int id = 0;

    float abv = 0;
    float sugar = 0;
    float sour = 0;
    float salty = 0;
    float bitter = 0;
    //추가한부분
    float hot = 0;

    float specific_gravity = 0;

    MJH_Object_color my_color;


    String flavour; // 스트링한개로 넣고 파싱할지 이런식으로 배열로 할지는 논의 필요
    public MJH_Object_ingredient(){}
    float alpha = 0;
    float muddy = 0;

    //추가한부분
    public MJH_Object_ingredient(String _name, float _specific_gravity , float _abv, float _sugar, float _sour, float _salty, float _bitter, float _hot, MJH_Object_color _my_color){
        this.name = _name;
        this.specific_gravity = _specific_gravity;
        this.abv =_abv;
        this.sugar = _sugar;
        this.sour = _sour;
        this.salty = _salty;
        this.bitter = _bitter;
        this.hot = _hot;

        this.my_color = _my_color;
    }
}
