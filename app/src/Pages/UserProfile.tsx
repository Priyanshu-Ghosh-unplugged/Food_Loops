import React from 'react';
import { Home, Search, Heart, User } from 'lucide-react';
import { Link } from 'react-router-dom';
import UserProfileComponent from '../components/UserProfile';

const UserProfile = () => {
  // Mock user data - in a real app, this would come from an API or state management
  const user = {
    name: "Sarah Anderson",
    fullName: "Sarah Elizabeth Anderson",
    email: "sarah.anderson@gmail.com",
    phone: "+1 (555) 123-4567",
    gender: "Female",
    address: "123 Maple Street, Brooklyn, NY 11201",
    bio: "Food enthusiast & Home chef",
    profileImage: "/lovable-uploads/bf0d7561-fd0d-405b-994a-4ced0da739e8.png"
  };

  const handleUpdateAccount = () => {
    console.log("Update account clicked");
    // In a real app, this would navigate to an edit profile page or open a modal
  };

  const handleDeleteAccount = () => {
    console.log("Delete account clicked");
    // In a real app, this would show a confirmation dialog
  };

  return (
    <>
      <UserProfileComponent 
        user={user}
        onUpdateAccount={handleUpdateAccount}
        onDeleteAccount={handleDeleteAccount}
      />
      
      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t flex justify-around py-3">
        <Link to="/" className="flex flex-col items-center text-gray-400">
          <Home size={20} />
          <span className="text-xs mt-1">Home</span>
        </Link>

        <div className="flex flex-col items-center text-gray-400">
          <Search size={20} />
          <span className="text-xs mt-1">Search</span>
        </div>

        <div className="flex flex-col items-center text-gray-400">
          <Heart size={20} />
          <span className="text-xs mt-1">Favorites</span>
        </div>

        <div className="flex flex-col items-center text-foodloops-green">
          <User size={20} />
          <span className="text-xs mt-1">Profile</span>
        </div>
      </div>
    </>
  );
};

export default UserProfile;
