import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    roomId: '',
    lastAction: '',
    auction: null,
    userData : {
        connected: false,
        message: "",
    },
    bidMessages: [],
    users: []
};

const auctionSlice = createSlice({
    name: "auction",
    initialState,
    reducers: {
        joinAuctionRoom: (state, action) => {
            state.roomId = action.payload.roomId;
            state.userData = action.payload.userData;
            state.auction = action.payload.auction;
            state.lastAction = 'joinAuctionRoomCompleted';
        },
        addBidMessages: (state, action) => {
            // if(action.payload?.isSendBid){
            //     state.bidMessages.push(action.payload);
            // }

            if (!state.bidMessages) state.bidMessages = [];
                state.bidMessages = action.payload;
        },
        removeBidMessages: (state, action) => {
            state.bidMessages = [];
        },
        addUsers: (state, action) => {
            //     if (!state.users) state.users = [];
            //     state.users.push(action.payload);
            if (!state.users) state.users = [];
            state.users = action.payload;
        },
        updateUserInRoom: (state, action) => {
            state.users = action.payload;
        },
        userOutRoom: (state, action) => {
            state.users = state.users.filter(user => user.user !== action.payload.email);
        },
        removeUsers: (state, action) => {
            state.users = [];
        },
        updatedAuctionRoom: (state, action) => {
            state.userData = { ...state.userData, ...action.payload };
            state.auction = null;
            state.roomId = '';
            state.lastAction = '';
        }
    },
});

export const {
    joinAuctionRoom,
    updatedAuctionRoom,
    addBidMessages,
    removeBidMessages,
    addUsers,
    removeUsers,
    userOutRoom,
    updateUserInRoom,
} = auctionSlice.actions;
export default auctionSlice.reducer;
export const auctionSelector = (state) => state.auction;