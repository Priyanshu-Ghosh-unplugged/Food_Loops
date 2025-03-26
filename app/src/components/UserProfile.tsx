import React from 'react';
import { ArrowLeft, Camera, User, Mail, Phone, User2, MapPin } from 'lucide-react';
import { Link } from 'react-router-dom';

type UserProfileProps = {
  user: {
    name: string;
    fullName: string;
    email: string;
    phone: string;
    gender: string;
    address: string;
    bio: string;
    profileImage: string;
  };
  onUpdateAccount?: () => void;
  onDeleteAccount?: () => void;
};

const UserProfile = ({ 
  user, 
  onUpdateAccount, 
  onDeleteAccount 
}: UserProfileProps) => {
  return (
    <div className="bg-gray-50 min-h-screen pb-20">
      {/* Header */}
      <header className="bg-white p-4 flex items-center shadow-sm">
        <Link to="/" className="mr-4">
          <ArrowLeft className="text-gray-700" size={20} />
        </Link>
        <h1 className="text-2xl font-bold italic flex-1 text-center">FoodLoops</h1>
        <div className="w-6"></div> {/* Empty div for alignment */}
      </header>

      {/* Profile Section */}
      <div className="bg-white p-6 flex flex-col items-center">
        <div className="relative">
          <div className="w-24 h-24 rounded-full bg-gray-200 overflow-hidden">
            <img 
              src={user.profileImage || "/lovable-uploads/bf0d7561-fd0d-405b-994a-4ced0da739e8.png"}
              alt="Profile" 
              className="w-full h-full object-cover" 
            />
          </div>
          <div className="absolute bottom-0 right-0 bg-white p-1 rounded-full shadow">
            <Camera size={18} className="text-gray-700" />
          </div>
        </div>

        <h2 className="mt-3 text-xl font-semibold">{user.name}</h2>
        <p className="text-gray-500 text-sm">{user.bio}</p>
      </div>

      {/* Profile Details */}
      <div className="bg-white mt-2 p-6">
        <div className="flex items-center mb-5">
          <User className="text-gray-700 mr-4" size={20} />
          <div>
            <p className="text-xs text-gray-500">Full Name</p>
            <p className="text-sm">{user.fullName}</p>
          </div>
        </div>

        <div className="flex items-center mb-5">
          <Mail className="text-gray-700 mr-4" size={20} />
          <div>
            <p className="text-xs text-gray-500">Email</p>
            <p className="text-sm">{user.email}</p>
          </div>
        </div>

        <div className="flex items-center mb-5">
          <Phone className="text-gray-700 mr-4" size={20} />
          <div>
            <p className="text-xs text-gray-500">Phone</p>
            <p className="text-sm">{user.phone}</p>
          </div>
        </div>

        <div className="flex items-center mb-5">
          <User2 className="text-gray-700 mr-4" size={20} />
          <div>
            <p className="text-xs text-gray-500">Gender</p>
            <p className="text-sm">{user.gender}</p>
          </div>
        </div>

        <div className="flex items-center mb-5">
          <MapPin className="text-gray-700 mr-4" size={20} />
          <div>
            <p className="text-xs text-gray-500">Address</p>
            <p className="text-sm">{user.address}</p>
          </div>
        </div>
      </div>

      {/* Buttons */}
      <div className="px-6 mt-4">
        <button 
          className="w-full bg-black text-white py-3 rounded-lg mb-3 flex justify-center items-center"
          onClick={onUpdateAccount}
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
          </svg>
          Update Account
        </button>
        
        <button 
          className="w-full border border-red-500 text-red-500 py-3 rounded-lg flex justify-center items-center"
          onClick={onDeleteAccount}
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
          </svg>
          Delete Account
        </button>
      </div>
    </div>
  );
};

export default UserProfile;
