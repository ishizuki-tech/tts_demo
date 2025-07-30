# Swahili TTS Sample Pack — with Translations (EN/日本語)

このREADMEは、Android等のTTS（テキスト読み上げ）検証のための**スワヒリ語サンプル集**です。
各行に **Swahili / English / 日本語** を並記しています。必要に応じて `.txt` へ抽出して1行＝1発話で再生してください。

This README provides a **Swahili text sample pack** for TTS verification on Android and elsewhere.
Each item includes **Swahili / English / Japanese** translations. You can export to `.txt` and feed **one line per utterance**.

## Quick use on Android (オフラインTTS想定)
- Put text in `assets/` and load with `AssetManager`, splitting by newline.
- Verify **offline** by enabling airplane mode and ensuring playback still works.
- Prefer an **embedded/ offline** Swahili voice (e.g., Google TTS with downloaded data, or eSpeak NG).

---

### 1) Everyday short lines

| Swahili | English | 日本語 |
|---|---|---|
| Habari? | How are you? / Hello? | 元気ですか？／こんにちは？ |
| Shikamoo! | Respectful greeting to an elder. | 目上の人への挨拶（シカモー）。 |
| Marahaba. | Response to “Shikamoo”. | 「シカモー」への返答（マラハバ）。 |
| Asante sana. | Thank you very much. | どうもありがとうございます。 |
| Karibu tena. | Welcome again. | またどうぞ／お帰りなさい。 |
| Samahani. | Sorry / Excuse me. | すみません／ごめんなさい。 |
| Pole na kazi. | Hang in there at work. (Empathy for hard work.) | お仕事お疲れさまです。 |
| Umeamkaje? | How did you wake up? (How’s your morning?) | 今朝の調子はどう？／よく眠れましたか？ |
| Nimeamka salama. | I woke up well. | 元気に起きました。 |
| Nimefurahi kukuona. | I’m happy to see you. | お会いできて嬉しいです。 |
| Tafadhali subiri kidogo. | Please wait a moment. | 少々お待ちください。 |
| Nitarudi baadaye. | I’ll come back later. | 後で戻ります。 |
| Sawa, tutaongea kesho. | Okay, we’ll talk tomorrow. | 分かりました、明日話しましょう。 |
| Hakuna shida. | No problem. | 問題ありません。 |
| Ndiyo. | Yes. | はい。 |
| Hapana. | No. | いいえ。 |
| Labda. | Maybe. | たぶん。 |
| Sijui bado. | I don’t know yet. | まだ分かりません。 |
| Ninaelewa. | I understand. | 分かります。 |
| Sielewi vizuri. | I don’t understand well. | よく分かりません。 |
| Twende sasa. | Let’s go now. | 今行きましょう。 |
| Ngoja kwanza. | Hold on a moment. | ちょっと待って。 |
| Kaa hapa, tafadhali. | Sit/stay here, please. | ここに座ってください／ここにいてください。 |
| Maji yako yapo hapa. | Your water is here. | お水はこちらです。 |
| Chai au kahawa? | Tea or coffee? | お茶かコーヒーはいかがですか？ |

### 2) Questions & confirmations

| Swahili | English | 日本語 |
|---|---|---|
| Je, unaweza kusikia sauti vizuri? | Can you hear the sound clearly? | 音声はよく聞こえますか？ |
| Uko tayari kuanza? | Are you ready to start? | 始める準備はいいですか？ |
| Umeelewa maelekezo? | Did you understand the instructions? | 指示は理解できましたか？ |
| Ungependa nirudie tena? | Would you like me to repeat? | もう一度繰り返しましょうか？ |
| Upo wapi sasa? | Where are you now? | 今どこにいますか？ |
| Ni saa ngapi kwako? | What time is it for you? | そちらは今何時ですか？ |
| Utasafiri lini? | When will you travel? | いつ出発しますか？ |
| Je, una maswali yoyote? | Do you have any questions? | 何か質問はありますか？ |
| Umechagua lugha ya Kiswahili? | Have you chosen the Swahili language? | スワヒリ語を選びましたか？ |
| Nithibitishe: unataka kuendelea? | Confirm: do you want to continue? | 確認です。続行しますか？ |

### 3) Numbers, dates, times

| Swahili | English | 日本語 |
|---|---|---|
| Saa ni mbili na dakika kumi. | The time is 2:10. | ただいま2時10分です。 |
| Leo ni Jumanne, tarehe 29 Julai 2025. | Today is Tuesday, July 29, 2025. | 今日は2025年7月29日（火）です。 |
| Tutakutana kesho saa 3:45 asubuhi. | We’ll meet tomorrow at 3:45 a.m. | 明日午前3時45分に会いましょう。 |
| Bei ni shilingi elfu mbili na mia tano. | The price is 2,500 shillings. | 価格は2,500シリングです。 |
| Namba ya simu: 0712 345 678. | Phone number: 0712 345 678. | 電話番号：0712 345 678 |
| Akaunti ina asilimia 12.5 ya riba kwa mwaka. | The account has 12.5% annual interest. | 口座の年利は12.5%です。 |
| Urefu ni mita 1.75, uzito kilo 68. | Height is 1.75 m, weight 68 kg. | 身長1.75メートル、体重68キロです。 |
| Tarehe ya mwisho ni 31/08/2025. | The deadline is 31/08/2025. | 締切は2025年8月31日です。 |
| Tumepokea maombi 1,234 jana. | We received 1,234 applications yesterday. | 昨日は1,234件の申請を受け取りました。 |
| Hali ya joto ni nyuzi 28; mvua inaendelea. | Temperature is 28 degrees; it’s still raining. | 気温は28度、雨が降り続いています。 |

### 4) Device & app commands

| Swahili | English | 日本語 |
|---|---|---|
| Bonyeza kitufe cha kuanza. | Press the start button. | 開始ボタンを押してください。 |
| Chagua lugha unayotaka. | Choose your preferred language. | 希望する言語を選択してください。 |
| Weka sauti kuwa ya chini. | Set the volume low. | 音量を小さくしてください。 |
| Zima mtandao ili kujaribu hali ya nje ya mtandao. | Turn off the network to test offline mode. | オフライン動作を試すためネットワークを切ってください。 |
| Pakua data za sauti za Kiswahili. | Download the Swahili voice data. | スワヒリ語の音声データをダウンロードしてください。 |
| Jaribu tena baada ya dakika moja. | Try again after one minute. | 1分後にもう一度お試しください。 |
| Hifadhi majibu yako. | Save your answers. | 回答を保存してください。 |
| Futa maandishi yote. | Clear all text. | テキストをすべて消去してください。 |
| Ruhusu ruhusa ya kipaza sauti. | Grant microphone permission. | マイクの権限を許可してください。 |
| Maliza mahojiano na thibitisha. | Finish the interview and confirm. | インタビューを終了して確認してください。 |

### 5) Location & directions

| Swahili | English | 日本語 |
|---|---|---|
| Tunaelekea soko la kijiji. | We are heading to the village market. | 村の市場に向かっています。 |
| Geuka kulia baada ya daraja. | Turn right after the bridge. | 橋を渡って右に曲がってください。 |
| Kaa upande wa kushoto wa barabara. | Keep to the left side of the road. | 道路の左側を走行してください。 |
| Tuko karibu na shule ya msingi. | We are near the primary school. | 小学校の近くにいます。 |
| Umbali hadi shamba ni kilomita mbili. | The distance to the farm is two kilometers. | 畑までの距離は2キロです。 |
| Ramani inaonyesha njia fupi zaidi. | The map shows the shortest route. | 地図は最短ルートを示しています。 |
| Fika saa kumi jioni bila kuchelewa. | Arrive at 4:00 p.m. without delay. | 夕方4時に遅れず到着してください。 |
| Tutakutana mbele ya zahanati. | We will meet in front of the clinic. | 診療所の前で会いましょう。 |
| Kuna foleni barabarani leo. | There is traffic on the road today. | 今日は道路が渋滞しています。 |
| Njia hii ni salama na kavu. | This road is safe and dry. | この道は安全で乾いています。 |

### 6) Agriculture domain

| Swahili | English | 日本語 |
|---|---|---|
| Je, unapanda mahindi au mtama msimu huu? | Are you planting maize or sorghum this season? | 今季はトウモロコシですか、それともソルガムですか？ |
| Ulipata mbegu gani: OPV au hybrid? | Which seeds did you get: OPV or hybrid? | どの種子を入手しましたか？OPVですか、それともハイブリッドですか？ |
| Umetumia mbolea aina gani, na kwa kiasi gani? | What type of fertilizer did you use, and how much? | どの種類の肥料をどれくらい使いましたか？ |
| Mvua ilianza lini kijijini kwenu? | When did the rains start in your village? | あなたの村ではいつ雨が始まりましたか？ |
| Shamba lako lina ukubwa wa hekta moja na nusu. | Your farm is one and a half hectares. | あなたの農地は1.5ヘクタールです。 |
| Ulipata mavuno gani msimu uliopita? | What yield did you get last season? | 前のシーズンはどれくらい収穫できましたか？ |
| Wadudu au magonjwa yameonekana shambani? | Have pests or diseases been observed on the farm? | 畑で害虫や病気は見られましたか？ |
| Bei ya mbegu sokoni ni shilingi elfu tano kwa kilo. | The seed price in the market is 5,000 shillings per kilo. | 市場の種子価格は1キロ5,000シリングです。 |
| Unapendelea aina ipi ya mahindi na kwa nini? | Which maize variety do you prefer and why? | どのトウモロコシ品種を好みますか？その理由は？ |
| Ulipulizia dawa mara ngapi, na siku zipi? | How many times did you spray, and on which days? | 防除は何回、どの日に行いましたか？ |
| Je, una mifugo kama ng’ombe au mbuzi? | Do you keep livestock such as cattle or goats? | 牛やヤギなどの家畜は飼っていますか？ |
| Umeuza mazao yako wapi mwaka huu? | Where did you sell your produce this year? | 今年はどこで作物を販売しましたか？ |
| Je, umepata mafunzo ya kilimo hifadhi? | Have you received training in conservation agriculture? | 保全農業の研修を受けましたか？ |
| Udongo wako ni wa kichanga au wa mfinyanzi? | Is your soil sandy or clayey? | 土壌は砂質ですか？それとも粘土質ですか？ |
| Una tumia mbinu za kupima unyevu ardhini? | Do you use methods to measure soil moisture? | 土壌水分を測る手法を使っていますか？ |
| Umepata ushauri kutoka kwa maafisa ugani? | Have you received advice from extension officers? | 普及員から助言を受けましたか？ |
| Umewahi kukosa pembejeo kwa sababu ya bei? | Have you ever lacked inputs because of price? | 価格のせいで資材が買えなかったことはありますか？ |
| Ulipata msaada wa mkopo wa pembejeo? | Did you receive input-credit support? | 資材購入のための融資支援を受けましたか？ |
| Msimu huu umechelewa kupanda kwa siku ngapi? | By how many days was planting delayed this season? | 今季の播種は何日遅れましたか？ |
| Ni changamoto gani kubwa katika kilimo chako? | What is the biggest challenge in your farming? | 農業で最も大きな課題は何ですか？ |

### 7) Public‑service / newsy lines

| Swahili | English | 日本語 |
|---|---|---|
| Taarifa rasmi: huduma za maji zitakatizwa kuanzia saa nne asubuhi. | Official notice: water service will be cut off from 10:00 a.m. | 公式通知：午前10時から断水します。 |
| Barabara ya kijiji itafungwa kwa matengenezo hadi kesho. | The village road will be closed for maintenance until tomorrow. | 村の道路は明日まで工事のため通行止めです。 |
| Kuna kliniki ya chanjo kesho saa tatu asubuhi katika zahanati ya kijiji. | There is a vaccination clinic tomorrow at 9:00 a.m. at the village dispensary. | 明日午前9時に村の診療所で予防接種クリニックがあります。 |
| Ripoti ya bei za mazao imeboreshwa leo. | The crop price report has been updated today. | 作物価格の報告は本日更新されました。 |
| Kikundi cha wakulima kitaonana Ijumaa jioni. | The farmers’ group will meet on Friday evening. | 農家グループは金曜の夕方に集まります。 |

### 8) Tongue twisters & phoneme coverage

| Swahili | English | 日本語 |
|---|---|---|
| Chungwa changu cheupe kimetoka sokoni. | My white orange came from the market. | 私の白いオレンジは市場から来ました。 |
| Shamba la babu lina mbegu nyingi nzuri. | Grandfather’s farm has many good seeds. | 祖父の畑には良い種がたくさんあります。 |
| Ng’ombe wangu wanane wanakunywa maji. | My eight cows are drinking water. | 私の八頭の牛が水を飲んでいます。 |
| Nyota njema huonekana asubuhi na jioni. | A good star appears in the morning and evening. | 良い星は朝と夕方に見えます。 |
| Kuku kumi wa kijijini wana kukuru kakara. | Ten village chickens are clucking. | 村の十羽のニワトリがコケコッコーと鳴いています。 |
| Kisima cha kijiji kimechimbwa karibu na shule. | The village well was dug near the school. | 村の井戸は学校の近くに掘られました。 |
| Mwangaza wa jua unawaka juu ya mlima. | Sunlight shines on top of the mountain. | 太陽の光が山の上に輝いています。 |

### 9) Punctuation & formatting tests

| Swahili | English | 日本語 |
|---|---|---|
| Habari, rafiki! Uko tayari—au tusubiri? | Hello, friend! Are you ready—or shall we wait? | やあ、友よ！準備はいい？—それとも待つ？ |
| “Karibu,” alisema mwalimu. ‘Asante,’ akajibu mwanafunzi. | "Welcome," said the teacher. 'Thank you,' replied the student. | 『ようこそ』と先生が言いました。『ありがとう』と生徒が答えました。 |
| Je, unaweza kusoma haya: 1) ingia; 2) chagua; 3) thibitisha? | Can you read this: 1) enter; 2) select; 3) confirm? | 次を読めますか：1) 進む；2) 選ぶ；3) 確認する？ |
| Faili limehifadhiwa (toleo: 1.0.0). | The file has been saved (version: 1.0.0). | ファイルを保存しました（バージョン：1.0.0）。 |
| Anwani: S.L.P. 123, Dar es Salaam. | Address: P.O. Box 123, Dar es Salaam. | 住所：S.L.P. 123、ダル・エス・サラーム。 |
| Tazama www.mfano.co.tz kwa maelezo zaidi. | See www.mfano.co.tz for more details. | 詳しくは www.mfano.co.tz をご覧ください。 |


### A) Crops & Planting Overview / 作付と播種の概要

| Swahili | English | 日本語 |
|---|---|---|
| Je, unalima mazao gani: mahindi, mtama, maharagwe, au mihogo? | Which crops are you growing: maize, sorghum, beans, or cassava? | どの作物を栽培していますか：トウモロコシ、ソルガム、インゲン、キャッサバ？ |
| Ulipanda tarehe gani msimu huu? | On what date did you plant this season? | 今季はいつ播種しましたか？ |
| Umbali kati ya mistari ni sentimita 75, kati ya mashimo ni sentimita 25. | Row spacing is 75 cm; hole spacing is 25 cm. | 畝間は75cm、株間は25cmです。 |
| Ulipanda mbegu ngapi kwa shimo: moja au mbili? | How many seeds per hole: one or two? | 1つの植穴に何粒まきますか：1粒、それとも2粒？ |
| Mbegu zimechukua siku ngapi kuchipua? | How many days did the seeds take to germinate? | 発芽まで何日かかりましたか？ |
| Tumia kina cha kupanda sentimita 3 hadi 5. | Use a planting depth of 3–5 cm. | 播種深は3～5cmを目安にしてください。 |

### B) Weeding & Field Operations / 除草・圃場管理

| Swahili | English | 日本語 |
|---|---|---|
| Fanya kupalilia mara ya kwanza wiki mbili baada ya kuchipua. | Do the first weeding two weeks after emergence. | 発芽後2週間で1回目の除草を行ってください。 |
| Fanya kupalilia mara ya pili wiki nne hadi tano baada ya kupanda. | Do the second weeding four to five weeks after planting. | 播種後4～5週間で2回目の除草を行ってください。 |
| Ondoa magugu kabla hayajatoa mbegu. | Remove weeds before they set seed. | 雑草が種を付ける前に除去してください。 |
| Tumia jembe jepesi au kifaa cha kulimia kupunguza udongo mgumu. | Use a light hoe or cultivator to break hard soil. | 硬い土は軽いクワやカルチベータでほぐしてください。 |

### C) Soil & Fertility / 土壌・肥培管理

| Swahili | English | 日本語 |
|---|---|---|
| Umetumia mbolea aina gani, na kwa kiasi gani kwa hekta? | Which type of fertilizer did you use, and how many kilograms per hectare? | どの種類の肥料を、1ヘクタール当たり何キロ使いましたか？ |
| Umetumia DAP wakati wa kupanda au NPK baada ya kuchipua? | Did you apply DAP at planting or NPK after emergence? | 播種時にDAPを、発芽後にNPKを施用しましたか？ |
| Umeweka samadi au mboji shambani kabla ya kupanda? | Did you apply farmyard manure or compost before planting? | 播種前に堆肥や厩肥を施用しましたか？ |
| Je, udongo wako una asidi nyingi? Umewahi kupima pH? | Does your soil have high acidity? Have you tested the pH? | 土壌は酸性が強いですか？pHを測定したことはありますか？ |
| Uliongeza chokaa (agricultural lime) kurekebisha asidi? | Did you add agricultural lime to correct acidity? | 酸度矯正のために炭酸カルシウム（苦土石灰など）を施用しましたか？ |

### D) Pests & Diseases / 病害虫

| Swahili | English | 日本語 |
|---|---|---|
| Je, umeona viwavi jeshi (fall armyworm) kwenye mahindi? | Have you observed fall armyworm on your maize? | トウモロコシにアワノメイガ（FAW）の被害はありますか？ |
| Majani yana madoa ya kutu au koga? | Do the leaves have rust spots or mildew? | 葉にさび病やうどんこ病の斑点はありますか？ |
| Umetumia dawa gani na kwa kiwango gani kwa lita? | Which pesticide did you use and at what rate per liter? | どの農薬を、1リットル当たりどの希釈率で使用しましたか？ |
| Je, unatumia IPM: ufuatiliaji, kuondoa mabuu kwa mikono, na mimea mseto? | Do you use IPM: monitoring, hand-picking larvae, and intercropping? | 総合的病害虫管理（IPM）：モニタリング、幼虫の手取り、混植などを行っていますか？ |
| Umetenga sehemu ya shamba kwa majaribio ya udhibiti? | Did you set aside a plot for control trials? | 防除の試験区を設けましたか？ |

### E) Irrigation & Weather / かんがい・気象

| Swahili | English | 日本語 |
|---|---|---|
| Unatumia umwagiliaji wa matone, kunyunyizia, au mifereji? | Do you use drip, sprinkler, or furrow irrigation? | 点滴灌漑、スプリンクラー、畝間灌漑のどれを使っていますか？ |
| Umehifadhi maji ya mvua kwenye matangi au mabirika? | Have you stored rainwater in tanks or ponds? | 雨水をタンクや貯水池に貯めていますか？ |
| Umefuata taarifa za hali ya hewa kabla ya kupanda? | Did you follow weather forecasts before planting? | 播種前に天気予報を参照しましたか？ |
| Mvua ya wiki iliyopita ilikuwa milimita ngapi? | How many millimeters of rain fell last week? | 先週の降雨量は何ミリでしたか？ |
| Umepima unyevu wa udongo kabla ya kumwagilia? | Did you measure soil moisture before irrigating? | 灌漑前に土壌水分を測定しましたか？ |

### F) Harvest & Post‑harvest / 収穫・収穫後

| Swahili | English | 日本語 |
|---|---|---|
| Ulivuna baada ya siku ngapi tangu kupanda? | After how many days from planting did you harvest? | 播種から何日後に収穫しましたか？ |
| Unatumia magunia ya kilo 90 au vifungashio vingine? | Do you use 90‑kg bags or other packaging? | 90kgの袋、それとも別の包装を使っていますか？ |
| Unakausha nafaka hadi unyevunyevu chini ya asilimia 13? | Do you dry grain to moisture below 13%? | 穀物の含水率を13%未満まで乾燥させていますか？ |
| Unatumia mifuko isiyopitisha hewa (PICS) kuhifadhia? | Do you use hermetic bags (PICS) for storage? | 密閉型袋（PICS）で保存していますか？ |
| Hasara baada ya mavuno imekuwa takriban asilimia ngapi? | Approximately what percent post‑harvest loss did you experience? | 収穫後損失はおよそ何％でしたか？ |

### G) Markets, Logistics & Finance / 市場・物流・金融

| Swahili | English | 日本語 |
|---|---|---|
| Bei ya mahindi sokoni leo ni shilingi ngapi kwa kilo? | What is today’s market price of maize per kilogram? | 本日のトウモロコシの市場価格は1キロあたり何シリングですか？ |
| Umeuza kwa dalali, soko la kijiji, au kiwandani? | Did you sell to a broker, village market, or at a factory? | 仲買人、村の市場、工場のどれに販売しましたか？ |
| Ulipata usafiri wa kubeba mazao kwa bei gani? | What transport cost did you pay to move produce? | 収穫物の輸送費はいくらかかりましたか？ |
| Je, unatumia mizani kupima uzito sahihi? | Do you use a scale to measure accurate weight? | 正確な重量を測るために秤を使っていますか？ |
| Ulipata mkopo wa pembejeo kutoka SACCO au benki? | Did you get input credit from a SACCO or bank? | SACCOや銀行から資材購入用の融資を受けましたか？ |
| Umenunua pembejeo kwa pamoja kupitia kikundi cha wakulima? | Did you do group purchasing of inputs through a farmer group? | 農家グループで共同購入を行いましたか？ |

### H) Crop‑specific Prompts / 作物別プロンプト

| Swahili | English | 日本語 |
|---|---|---|
| (Mahindi) Umetumia mbegu zilizothibitishwa na taasisi? | (Maize) Did you use certified seed from an accredited supplier? | （トウモロコシ）認定供給者の認証種子を使用しましたか？ |
| (Mahindi) Ulipunguza shina kwa ajili ya silaji ya mifugo? | (Maize) Did you chop stalks for livestock silage? | （トウモロコシ）家畜用サイレージのために茎を裁断しましたか？ |
| (Mtama) Umechagua aina inayostahimili ukame? | (Sorghum) Did you choose a drought‑tolerant variety? | （ソルガム）耐乾燥性のある品種を選びましたか？ |
| (Mtama) Umefanya kupunguza msongamano wa miche? | (Sorghum) Did you thin seedlings to reduce crowding? | （ソルガム）過密を避けるため間引きを行いましたか？ |
| (Maharagwe) Umetumia chanjo ya rhizobia kabla ya kupanda? | (Beans) Did you use rhizobium inoculant before planting? | （インゲン）播種前にリゾビウムの接種剤を使いましたか？ |
| (Maharagwe) Umetumia miti ya kutegemeza maharagwe marefu? | (Beans) Did you stake climbing beans? | （インゲン）つる性インゲンに支柱を立てましたか？ |
| (Mihogo) Umetumia vipandikizi safi visivyo na ugonjwa? | (Cassava) Did you use clean, disease‑free cuttings? | （キャッサバ）病気のない健全な挿し木を使用しましたか？ |
| (Mihogo) Umeona dalili za CMD au CBSD? | (Cassava) Have you seen CMD or CBSD symptoms? | （キャッサバ）CMD（モザイク病）やCBSD（褐条病）の症状は見られましたか？ |

### I) Extension & Training / 普及・研修

| Swahili | English | 日本語 |
|---|---|---|
| Umeshiriki mafunzo ya shamba darasa (Farmer Field School)? | Have you attended a Farmer Field School training? | 農民圃場学校（FFS）の研修に参加しましたか？ |
| Je, umepokea SMS za ushauri wa kilimo msimu huu? | Have you received advisory SMS messages this season? | 今季、農業アドバイスのSMSを受け取りましたか？ |
| Maafisa ugani hutembelea mara ngapi kwa mwezi? | How many times per month do extension officers visit? | 普及員は月に何回訪問しますか？ |
| Je, unatumia kanuni za usalama wakati wa kupulizia dawa? | Do you follow safety rules when spraying pesticides? | 農薬散布時の安全基準を守っていますか？ |

### J) Enumerator Prompts / 調査員定型文

| Swahili | English | 日本語 |
|---|---|---|
| Tafadhali taja tarehe kamili kwa mfumo wa DD/MM/YYYY. | Please state the full date in the format DD/MM/YYYY. | 日付はDD/MM/YYYY形式で入力してください。 |
| Taja vipimo vyote kwa kilo, lita, au hekta. | Give all measurements in kilograms, liters, or hectares. | 数量はキログラム、リットル、またはヘクタールで入力してください。 |
| Kama hujui kiwango sahihi, tafadhali toa makadirio yako. | If you don’t know the exact amount, please provide an estimate. | 正確な値が分からない場合は概算で回答してください。 |
| Rudia jibu lako kwa uthibitisho, tafadhali. | Please repeat your answer for confirmation. | 確認のため、もう一度回答を繰り返してください。 |
| Shukrani kwa ushirikiano wako. | Thank you for your cooperation. | ご協力ありがとうございます。 |
